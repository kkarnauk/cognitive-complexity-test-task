package org.jetbrains.cognitivecomplexity.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.util.parentOfType
import org.jetbrains.cognitivecomplexity.bundle.Bundle
import org.jetbrains.cognitivecomplexity.dialog.InfoDialogWrapper
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.idea.core.util.getLineCount
import org.jetbrains.kotlin.idea.util.application.runWriteAction
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory
import java.awt.event.ActionEvent
import javax.swing.Action

class KotlinCurrentMethodInfoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.dataContext.getData(CommonDataKeys.CARET)?.offset?.let { offset ->
            e.dataContext.getData(CommonDataKeys.PSI_FILE)?.findElementAt(offset)?.let { element ->
                element.parentOfType<KtNamedFunction>()?.let { method ->
                    MethodInfoDialogWrapper(method).show()
                }
            }
        } ?: NoMethodInfoDialogWrapper().show()
    }

    override fun update(e: AnActionEvent) {
        val shouldEnable = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)?.fileType === KotlinFileType.INSTANCE
        e.presentation.isVisible = shouldEnable
        e.presentation.isEnabled = shouldEnable
    }

    private class MethodInfoDialogWrapper(private val method: KtNamedFunction) : InfoDialogWrapper(
        Bundle.message("currentMethodInfoTitle"),
        Bundle.message(
            "currentMethodInfoDialog",
            method.nameOrErrorMessage,
            method.getLineCount()
        )
    ) {
        override fun createActions(): Array<Action> = super.createActions() + AddInfoCommentAction()

        private inner class AddInfoCommentAction : DialogWrapperAction(Bundle.message("addCommentButtonName")) {
            override fun doAction(e: ActionEvent?) {
                val comment = KtPsiFactory(method.project).createComment(
                    Bundle.message(
                        "methodCommentText",
                        method.nameOrErrorMessage,
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

    private class NoMethodInfoDialogWrapper : InfoDialogWrapper(
        Bundle.message("currentMethodInfoTitle"),
        Bundle.message("methodNotFoundError")
    )

    companion object {
        private val KtNamedFunction.nameOrErrorMessage get(): String =
            name ?: Bundle.message("noMethodNameError")
    }
}
