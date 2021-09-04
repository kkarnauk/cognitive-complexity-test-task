package com.github.kkarnauk.cognitivecomplexitytesttask.services

import com.github.kkarnauk.cognitivecomplexitytesttask.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
