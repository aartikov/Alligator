package me.aartikov.alligator

/**
 * Marks that a screen is flow screen. Should be used for fragments only. For fragments that are registered as flow screens
 * navigation is configured by `flowFragmentNavigation` method of [me.aartikov.alligator.NavigationContext.Builder].
 */
interface FlowScreen : Screen