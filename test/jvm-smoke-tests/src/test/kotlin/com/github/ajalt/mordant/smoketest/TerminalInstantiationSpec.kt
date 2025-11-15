package com.github.ajalt.mordant.smoketest

import com.github.ajalt.mordant.terminal.Terminal
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TerminalInstantiationSpec : StringSpec({

    "can instantiate a Terminal" {
        val terminal = Terminal()
        terminal.terminalInfo.interactive shouldBe true
    }
})
