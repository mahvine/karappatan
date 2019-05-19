import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaseSummary } from 'app/shared/model/case-summary.model';
import { ModuleService } from 'app/entities/module';
import { IModule } from 'app/shared/model/module.model';
import { KarappatanService } from './karappatan.service';
import { AccountService } from 'app/core';
import { JhiConfigurationService } from 'app/admin';

@Component({
    selector: 'jhi-case-summary-detail',
    templateUrl: './case-summary-detail.component.html'
})
export class CaseSummaryDetailComponent implements OnInit {
    currentAccount: any;
    caseSummary: ICaseSummary;
    module: IModule;
    configuration: any[] = [];
    configKeys: any[] = [];

    constructor(
        protected activatedRoute: ActivatedRoute,
        private moduleService: ModuleService,
        private karappatanService: KarappatanService,
        private accountService: AccountService,
        private configurationService: JhiConfigurationService
    ) {}

    ngOnInit() {
        this.configurationService.get().subscribe(configuration => {
            this.configuration = configuration.filter(config => {
                if (config.prefix === 'application') {
                    return true;
                }
                return false;
            })[0].properties; // caseSummaryAcceptanceEnabled

            for (const config of configuration) {
                if (config.properties !== undefined) {
                    this.configKeys.push(Object.keys(config.properties));
                }
            }
        });
        this.activatedRoute.data.subscribe(({ caseSummary }) => {
            this.caseSummary = caseSummary;
            if (this.caseSummary.moduleId !== undefined) {
                this.moduleService.find(this.caseSummary.moduleId).subscribe(response => {
                    this.module = response.body;
                });
            }
        });
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            console.log(this.currentAccount);
        });
    }

    previousState() {
        window.history.back();
    }

    accept() {
        this.karappatanService.accept(this.caseSummary.id).subscribe(response => {
            this.caseSummary = response.body;
        });
    }
}
