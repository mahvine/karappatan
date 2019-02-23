import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { KarappatanSharedModule } from 'app/shared';
import { CaseSummaryComponent, cmsState } from './';

@NgModule({
    imports: [KarappatanSharedModule, RouterModule.forChild(cmsState)],
    declarations: [CaseSummaryComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarappatanCmsModule {}
