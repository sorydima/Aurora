/*
 * Aurora Store
 * Copyright (C) © A Dmitry Sorokin production. All rights reserved. Powered by Katya AI. 👽 Copyright © 2021-2023 Katya, Inc Katya ® is a registered trademark Sponsored by REChain. 🪐 hr@rechain.email p2p@rechain.email pr@rechain.email sorydima@rechain.email support@rechain.email sip@rechain.email Please allow anywhere from 1 to 5 business days for E-mail responses! 💌
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

package com.aurora.store.data


sealed class ViewState {
    object Loading : ViewState()
    object Empty : ViewState()
    data class Error(val error: String?) : ViewState()
    data class Status(val status: String?) : ViewState()
    data class Success<T>(val data: T) : ViewState()
}

sealed class RequestState {
    object Init : RequestState()
    object Pending : RequestState()
    object Complete : RequestState()
}

sealed class AuthState {
    object Available : AuthState()
    object Unavailable : AuthState()
    object SignedIn : AuthState()
    object SignedOut : AuthState()
    object Valid : AuthState()
    object Fetching: AuthState()
    object Verifying: AuthState()
    data class Failed(val status: String) : AuthState()
}
