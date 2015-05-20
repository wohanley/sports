package com.wohanley.sports

import com.wohanley.robots.grammar.generative.randomText
import com.wohanley.robots.grammar.generative.GenerativeGrammar


package object grammar {

  import scala.util.Random


  private val modifiers = Set(
    new GenerativeGrammar {
      // arenas
      'start      produces ("at home" or "away" or
                            ("in " ~ 'inLocation) or
                            ("on " ~ 'onLocation))
      'inLocation produces ("Halifax" or "Charlottetown" or "Vancouver" or
        "Toronto" or "Montreal" or "the North End" or "space" or "Wellington" or
        "Cheonan" or "big cities" or "sleepy hamlets" or "a colosseum" or
        "forests" or "meadows" or "gender-swapped AUs" or "circus tents")
      'onLocation produces ("a hillside" or "a mountain" or "a barge" or
        "frozen lakes" or "top of tall buildings" or "loam" or "a peat bog")
    },
    new GenerativeGrammar {
      // audiences
      'start produces "before a judge"
    },
    new GenerativeGrammar {
      // seasons
      'start  produces (("in " ~ 'season) or
                        ("during " ~ 'season))
      'season produces ("spring" or "summer" or "fall" or "autumn" or "winter")
    })

  def getModifiers(numberOfModifiers: Int): String =
    Random.shuffle(modifiers.toSeq).take(numberOfModifiers).map { grammar =>
      randomText(grammar.builtGrammar).mkString
    }.mkString(" ")

  def getSomeModifiers: String = getModifiers(Random.nextInt(2) + 1)

  object SportsGrammar extends GenerativeGrammar {
    'start produces (('team ~ " " ~ 'performanceAspect ~ " " ~
                      (() => getSomeModifiers)) or
                     ((() => getSomeModifiers) ~ ", " ~ 'team ~ " " ~
                       'performanceAspect))

    'team produces ("the Rangers" or "the Canadiens" or "the Golden Seals" or
      "the Rainmen" or "the Sabres" or "the Devils" or "moon team" or
      "sun team" or "the Abbies" or "the Bears" or "real programmers")

    'performanceAspect produces ('quantitativePerformance or
      'qualitativePerformance)

    'quantitativePerformance produces ('quantitativeVerb ~ " " ~ 'magnitude ~
      " " ~ 'quantitativeComparison ~ " " ~ 'performanceQuantity)
    'quantitativeVerb produces ("score" or "get")
    'quantitativeComparison produces ("more" or "fewer")
    'performanceQuantity produces ("goals" or "points" or "touchdowns" or
      "runs" or "wins" or "losses" or "gold stars" or "favs")

    'qualitativePerformance produces ('qualitativeVerb ~ " " ~ 'magnitude ~
      " " ~ 'qualitativeComparison ~ " " ~ 'performanceQuality)
    'qualitativeVerb produces ("have" or "get" or "score")
    'qualitativeComparison produces ("nicer" or "better" or "worse")
    'performanceQuality produces ("batting average" or "sportsmanship" or
      "power play percentage" or "penalty killing" or "running" or "focus")

    'magnitude produces ('percentage or 'multiple)
    'percentage produces ((() => (Random.nextInt(100) + 1).toString) ~ "%")
    'multiple produces ((() => (Random.nextInt(5) + 2).toString) ~ " times")
  }
}
