package org.jetbrains.cognitivecomplexity.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import org.jetbrains.cognitivecomplexity.bundle.Bundle
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.KtNamedFunction
import java.awt.BorderLayout
import javax.swing.Action
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class KotlinCurrentMethodInfoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.dataContext.getData(CommonDataKeys.CARET)?.offset?.let { offset ->
            e.dataContext.getData(CommonDataKeys.PSI_FILE)?.findElementAt(offset)?.let { element ->
                MethodInfo.of(element)?.let { info ->
                    InfoDialogWrapper(info).show()
                }
            }
        } ?: NoInfoDialogWrapper().show()
    }

    override fun update(e: AnActionEvent) {
        val shouldEnable = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)?.fileType === KotlinFileType.INSTANCE
        e.presentation.isVisible = shouldEnable
        e.presentation.isEnabled = shouldEnable
    }

    private class InfoDialogWrapper(private val methodInfo: MethodInfo) : DialogWrapper(true) {
        init {
            title = Bundle.message("currentMethodInfoTitle")
            init()
        }

        override fun createCenterPanel(): JComponent {
            val info = JLabel(
                Bundle.message(
                    "currentMethodInfoDialog",
                    methodInfo.name
                )
            )
            return JPanel(BorderLayout()).apply {
                add(info, BorderLayout.CENTER)
            }
        }

        override fun createActions(): Array<Action> = arrayOf(okAction)
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

    private data class MethodInfo(val name: String) {
        companion object {
            fun of(element: PsiElement): MethodInfo? =
                element.parentOfType<KtNamedFunction>()?.name?.let { MethodInfo(it) }
        }
    }
}
