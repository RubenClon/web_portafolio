package org.ruben.web_personal

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform