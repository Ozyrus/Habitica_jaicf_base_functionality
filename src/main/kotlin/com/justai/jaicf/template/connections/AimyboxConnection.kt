package com.justai.jaicf.template.connections
import com.justai.jaicf.channel.http.httpBotRouting
import com.justai.jaicf.channel.aimybox.AimyboxChannel
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import com.justai.jaicf.template.templateBot

fun main() {
    embeddedServer(Netty, System.getenv("PORT")?.toInt() ?: 8080) {
        routing {
            httpBotRouting("/" to AimyboxChannel(templateBot))
        }
    }.start(wait = true)
}