package com.eakurnikov.autoque.autofill.impl.di.components

import com.eakurnikov.autoque.autofill.impl.internal.domain.AutoQueAutofillService
import com.eakurnikov.autoque.autofill.impl.internal.ui.disclaimer.DisclaimerActivity
import com.eakurnikov.autoque.autofill.impl.internal.ui.viewall.ViewAllActivity
import com.eakurnikov.autoque.autofill.impl.internal.ui.update.UpdatePromptActivity
import com.eakurnikov.common.di.annotations.AutofillSessionScope
import com.eakurnikov.common.di.initializer.ComponentInitializer
import dagger.Subcomponent

/**
 * Created by eakurnikov on 2019-09-15
 */
@AutofillSessionScope
@Subcomponent
abstract class AutofillServiceComponent {

    companion object : ComponentInitializer<Unit, AutofillServiceComponent>() {
        override fun buildComponent(param: Unit): AutofillServiceComponent {
            return AutofillFeatureComponent.get().createAutofillServiceComponent()
        }
    }

    abstract fun inject(autoQueAutofillService: AutoQueAutofillService)

    abstract fun inject(updatePromptActivity: UpdatePromptActivity)

    abstract fun inject(disclaimerActivity: DisclaimerActivity)

    abstract fun inject(viewAllActivity: ViewAllActivity)
}