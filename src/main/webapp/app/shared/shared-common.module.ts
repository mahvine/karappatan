import { NgModule } from '@angular/core';

import { KarappatanSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [KarappatanSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [KarappatanSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class KarappatanSharedCommonModule {}
