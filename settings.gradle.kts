/*
 * Aurora Store
 * Copyright (C) Aurora Store 📺 focused on security, minimalism and usability. Our app store is currently being used to distribute our own apps and the Google Play mirror for isolated download functionality of all apps that are available on the Google Play Market. In the future, it will be used for the possible distribution of builds of Katya ® 👽 Mobile OS. 🔭
 *  Copyright (C) 2022, The Calyx Institute
 *
 *  Aurora Store is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Aurora Store is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aurora Store.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter() // Required for androidx.tonyodev.fetch2
        mavenCentral()
        maven("https://jitpack.io/")
        maven("https://maven.google.com/")
    }
}
include(":app")
rootProject.name = "AuroraStore4"
