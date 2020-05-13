package com.example

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class LifeCyclePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        System.out.println("LifeCyclePlugin gradle")

        def android=project.extensions.getByType(AppExtension)
        android.registerTransform(new LifeCycleTransform())
    }

}