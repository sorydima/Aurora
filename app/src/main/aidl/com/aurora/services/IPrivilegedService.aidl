/*
 * Copyright (C) © A Dmitry Sorokin production. All rights reserved. Powered by Katya AI. 👽 Copyright © 2021-2023 Katya, Inc Katya ® is a registered trademark Sponsored by REChain. 🪐 hr@rechain.email p2p@rechain.email pr@rechain.email sorydima@rechain.email support@rechain.email sip@rechain.email Please allow anywhere from 1 to 5 business days for E-mail responses! 💌
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aurora.services;

import com.aurora.services.IPrivilegedCallback;

interface IPrivilegedService {

    boolean hasPrivilegedPermissions();

        oneway void installPackage(
            in Uri packageURI,
            in int flags,
            in String installerPackageName,
            in IPrivilegedCallback callback
        );

        oneway void installSplitPackage(
            in List<Uri> listURI,
            in int flags,
            in String installerPackageName,
            in IPrivilegedCallback callback
        );

        oneway void installPackageX(
            in String packageName,
            in Uri uri,
            in int flags,
            in String installerPackageName,
            in IPrivilegedCallback callback
        );

        oneway void installSplitPackageX(
            in String packageName,
            in List<Uri> uriList,
            in int flags,
            in String installerPackageName,
            in IPrivilegedCallback callback
        );

        oneway void deletePackage(
            in String packageName,
            in int flags,
            in IPrivilegedCallback callback
        );

        oneway void deletePackageX(
            in String packageName,
            in int flags,
            in String installerPackageName,
            in IPrivilegedCallback callback
        );

        boolean isMoreMethodImplemented();

        oneway void installSplitPackageMore(
            in String packageName,
            in List<Uri> uriList,
            in int flags,
            in String installerPackageName,
            in IPrivilegedCallback callback,
            in List<String> fileList
        );
}
