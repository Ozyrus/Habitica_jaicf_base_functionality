package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.channel.aimybox.AimyboxEvent
import com.justai.jaicf.channel.aimybox.aimybox
import com.justai.jaicf.model.scenario.Scenario

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


        state("change_view") {
            activators {
                intent("change_view")
            }
            action {
                var slot = ""
                reactions.say("Перехожу..." )
                activator.caila?.run {slot = slots["views"].toString()}
                reactions.aimybox?.response?.action = "change_view"
                reactions.aimybox?.response?.intent = slot

            }
        }

        state("create_task") {
            activators {
                intent("create_task")
            }
            action {
                var slot = ""
                reactions.say("Перехожу..." )
                activator.caila?.run {slot = slots["tasks"].toString()}
                reactions.aimybox?.response?.action = "create_task"
                reactions.aimybox?.response?.intent = slot

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