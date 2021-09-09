package org.jetbrains.cognitivecomplexity.action

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import org.jetbrains.cognitivecomplexity.bundle.Bundle

class CurrentFileTypeAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val fileTypeName = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)?.fileType?.displayName
            ?: Bundle.message("noCurrentFileError")
        notificationGroup.createNotification(
            Bundle.message("currentFileTypeNotification", fileTypeName),
            NotificationType.INFORMATION
        ).notify(e.project)
    }

    companion object {
        private val notificationGroup by lazy {
            NotificationGroup.balloonGroup(Bundle.message("notificationGroup"))
        }
    }
}
