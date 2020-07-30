package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.channel.aimybox.AimyboxEvent
import com.justai.jaicf.channel.aimybox.aimybox
import com.justai.jaicf.model.scenario.Scenario
import kotlinx.serialization.json.*

object MainScenario : Scenario() {

    init {
        state("Start") {
            globalActivators {
                regex("/start")
                event(AimyboxEvent.START)
            }
            action {
                reactions.say("So let's begin!")
            }
        }
        state("Hello") {
            activators {
                intent("Hello")
            }

            action {
                reactions.say("Hi there!")
            }
        }
        state("Bye") {
            activators {
                intent("Bye")
            }

            action {
                reactions.say("See you soon!")
            }
        }


        state("changeView") {
            activators {
                intent("changeView")
            }
            action {
                var slot = ""
                reactions.say("Перехожу..." )
                activator.caila?.run {slot = slots["views"].toString()}
                reactions.aimybox?.response?.action = "changeView"
                reactions.aimybox?.response?.intent = slot

            }
        }

        state("createTask") {
            activators {
                intent("createTask")
            }
            action {
                var taskType = ""
                var taskName:JsonLiteral? = null
                var taskDescription:JsonLiteral? = null
                var taskSentiment:JsonLiteral? = null
                var taskDifficulty:JsonLiteral? = null
                reactions.say("Перехожу..." )
                activator.caila?.run {
                    taskType = slots["task_type"].toString()
                    taskName = JsonLiteral(slots["1"].toString())
                    taskDescription = JsonLiteral(slots["2"].toString())
                    taskSentiment = slots["sentiment"]?.toBoolean()?.let { JsonLiteral(it) }
                    taskDifficulty = JsonLiteral(slots["difficulty"].toString())
                }
                reactions.aimybox?.response?.action = "createTask"
                reactions.aimybox?.response?.intent = taskType
                taskName?.let { reactions.aimybox?.response?.data?.put("taskName", it) }
                taskDescription?.let { reactions.aimybox?.response?.data?.put("taskDescription", it) }
                taskSentiment?.let { reactions.aimybox?.response?.data?.put("taskSentiment", it) }
                taskDifficulty?.let { reactions.aimybox?.response?.data?.put("taskDifficulty", it) }
            }
        }

        state("fallback", noContext = true) {
            activators {
                catchAll()
            }

            action {
                reactions.say("Ты попал по адресу")
            }
        }
    }
}