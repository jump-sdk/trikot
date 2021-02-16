package com.mirego.trikot.viewmodels.declarative.resource

import com.mirego.trikot.foundation.concurrent.freeze

interface TextAppearanceResource {
    companion object {
        val None = freeze(NoTextAppearanceResource() as TextAppearanceResource)
    }
}

class NoTextAppearanceResource : TextAppearanceResource
