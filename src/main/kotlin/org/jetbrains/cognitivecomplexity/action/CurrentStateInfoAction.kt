package org.jetbrains.cognitivecomplexity.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.DialogWrapper
import org.jetbrains.cognitivecomplexity.bundle.Bundle
import java.awt.BorderLayout
import javax.swing.Action
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class CurrentStateInfoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val projectName = e.project?.name ?: Bundle.message("noCurrentProjectError")
        val fileName = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)?.name ?: Bundle.message("noCurrentFileError")
        InfoDialogWrapper(StateInfo(projectName, fileName)).show()
    }

    private class InfoDialogWrapper(private val stateInfo: StateInfo) : DialogWrapper(true) {
        init {
            title = Bundle.message("currentStateInfoTitle")
            init()
        }

        override fun createCenterPanel(): JComponent {
            val info = JLabel(
                Bundle.message(
                    "currentStateInfoDialog",
                    stateInfo.projectName,
                    stateInfo.fileName
                )
            )
            return JPanel(BorderLayout()).apply {
                add(info, BorderLayout.CENTER)
            }
        }

        override fun createActions(): Array<Action> = arrayOf(okAction)
    }

    private data class StateInfo(val projectName: String, val fileName: String)
}
