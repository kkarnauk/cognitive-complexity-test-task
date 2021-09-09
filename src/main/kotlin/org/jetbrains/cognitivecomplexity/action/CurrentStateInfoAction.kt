package org.jetbrains.cognitivecomplexity.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import org.jetbrains.cognitivecomplexity.bundle.Bundle
import org.jetbrains.cognitivecomplexity.dialog.InfoDialogWrapper

class CurrentStateInfoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val projectName = e.project?.name ?: Bundle.message("noCurrentProjectError")
        val fileName = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)?.name ?: Bundle.message("noCurrentFileError")
        InfoDialogWrapper(
            Bundle.message("currentStateInfoTitle"),
            Bundle.message(
                "currentStateInfoDialog",
                projectName,
                fileName
            )
        ).show()
    }
}
