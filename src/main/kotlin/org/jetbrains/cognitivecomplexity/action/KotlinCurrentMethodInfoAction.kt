package org.jetbrains.cognitivecomplexity.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.util.parentOfType
import org.jetbrains.cognitivecomplexity.bundle.Bundle
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.idea.core.util.getLineCount
import org.jetbrains.kotlin.idea.util.application.runWriteAction
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import javax.swing.Action
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class KotlinCurrentMethodInfoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.dataContext.getData(CommonDataKeys.CARET)?.offset?.let { offset ->
            e.dataContext.getData(CommonDataKeys.PSI_FILE)?.findElementAt(offset)?.let { element ->
                element.parentOfType<KtNamedFunction>()?.let { method ->
                    InfoDialogWrapper(method).show()
                }
            }
        } ?: NoInfoDialogWrapper().show()
    }

    override fun update(e: AnActionEvent) {
        val shouldEnable = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)?.fileType === KotlinFileType.INSTANCE
        e.presentation.isVisible = shouldEnable
        e.presentation.isEnabled = shouldEnable
    }

    private class InfoDialogWrapper(private val method: KtNamedFunction) : DialogWrapper(true) {
        init {
            title = Bundle.message("currentMethodInfoTitle")
            init()
        }

        override fun createCenterPanel(): JComponent {
            val info = JLabel(
                Bundle.message(
                    "currentMethodInfoDialog",
                    method.name ?: Bundle.message("noMethodNameError"),
                    method.getLineCount() + 1
                )
            )
            return JPanel(BorderLayout()).apply {
                add(info, BorderLayout.CENTER)
            }
        }

        override fun createActions(): Array<Action> = arrayOf(AddInfoCommentAction(), okAction)

        private inner class AddInfoCommentAction : DialogWrapperAction(Bundle.message("addCommentButtonName")) {
            override fun doAction(e: ActionEvent?) {
                val comment = KtPsiFactory(method.project).createComment(
                    Bundle.message(
                        "methodCommentText",
                        method.name ?: Bundle.message("noMethodNameError"),
                        method.getLineCount()
                    )
                )
                runWriteAction {
                    WriteCommandAction.runWriteCommandAction(method.project) {
                        method.addBefore(comment, method.funKeyword)
                    }
                }

                close(OK_EXIT_CODE)
            }
        }
    }

    private class NoInfoDialogWrapper : DialogWrapper(true) {
        init {
            title = Bundle.message("currentMethodInfoTitle")
            init()
        }

        override fun createCenterPanel(): JComponent {
            val info = JLabel(Bundle.message("methodNotFoundError"))
            return JPanel(BorderLayout()).apply {
                add(info, BorderLayout.CENTER)
            }
        }

        override fun createActions(): Array<Action> = arrayOf(okAction)
    }
}
