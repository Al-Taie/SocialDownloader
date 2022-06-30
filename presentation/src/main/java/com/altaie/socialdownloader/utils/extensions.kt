package com.altaie.socialdownloader.utils

import android.net.Uri


fun Uri?.toUrlOrNull() = this?.host?.plus(path)

