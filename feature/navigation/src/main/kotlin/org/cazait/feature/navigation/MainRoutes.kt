package org.cazait.feature.navigation

object MainRoutes {
    fun isMainRoute(routeName: String?): Boolean {
        return routeName == HomeNav.routeName
    }
}
