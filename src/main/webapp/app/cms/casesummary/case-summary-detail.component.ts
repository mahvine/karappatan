import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaseSummary } from 'app/shared/model/case-summary.model';
import { ModuleService } from 'app/entities/module';
import { IModule } from 'app/shared/model/module.model';

@Component({
    selector: 'jhi-case-summary-detail',
    templateUrl: './case-summary-detail.component.html'
})
export class CaseSummaryDetailComponent implements OnInit {
    caseSummary: ICaseSummary;
    module: IModule;

    constructor(protected activatedRoute: ActivatedRoute, private moduleService: ModuleService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caseSummary }) => {
            this.caseSummary = caseSummary;
            if (this.caseSummary.moduleId !== undefined) {
                this.moduleService.find(this.caseSummary.moduleId).subscribe(response => {
                    this.module = response.body;
                });
            }
        });
    }

    previousState() {
        window.history.back();
    }
}
