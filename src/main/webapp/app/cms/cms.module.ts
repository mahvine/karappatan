import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { KarappatanSharedModule } from 'app/shared';
import { CasesummaryComponent, cmsState } from './';

@NgModule({
    imports: [KarappatanSharedModule, RouterModule.forChild(cmsState)],
    declarations: [CasesummaryComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarappatanCmsModule {}
