package com.jxareas.techhub.ui.common.events

interface OnInteraction<T, ID> : OnClicked<T>, OnSwiped<ID>, OnLongPressed<ID>
