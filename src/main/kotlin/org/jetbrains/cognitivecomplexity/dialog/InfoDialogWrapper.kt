package org.jetbrains.cognitivecomplexity.dialog

import com.intellij.openapi.ui.DialogWrapper
import java.awt.BorderLayout
import javax.swing.*

open class InfoDialogWrapper(
    title: String,
    private val infoContent: String
) : DialogWrapper(true) {

    init {
        this.title = title
        setButtonsAlignment(SwingUtilities.CENTER)
        init()
    }

    final override fun init() = super.init()

    override fun createCenterPanel(): JComponent? {
        val infoLabel = JLabel(infoContent)
        return JPanel(BorderLayout()).apply {
            add(infoLabel, BorderLayout.CENTER)
        }
    }

    override fun createActions(): Array<Action> = arrayOf(okAction)
}
