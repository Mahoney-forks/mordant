package com.github.ajalt.mordant.smoketest

import com.github.ajalt.mordant.terminal.Terminal
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.StringSpec

class TerminalInstantiationSpec : StringSpec({

    "can instantiate a Terminal" {
        shouldNotThrowAny {
            Terminal()
        }
    }
})
