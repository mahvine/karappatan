import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICaseSummary } from 'app/shared/model/case-summary.model';
import { IModule } from 'app/shared/model/module.model';
import { IQuestion } from 'app/shared/model/question.model';

import { JhiConfigurationService } from 'app/admin';
import { AccountService } from 'app/core';
import { ModuleService } from 'app/entities/module';
import { KarappatanService } from './karappatan.service';
import { IAnswer } from 'app/shared/model/answer.model';
import { ICaseSummaryOffer, OfferStatus } from 'app/shared/model/case-summary-offer.model';

@Component({
    selector: 'jhi-case-summary-detail',
    templateUrl: './case-summary-detail.component.html'
})
export class CaseSummaryDetailComponent implements OnInit {
    currentAccount: any;
    caseSummary: ICaseSummary;
    module: IModule;
    configKeys: any[] = [];
    sortedQuestions: IQuestion[] = [];
    recommendations: string[] = [];
    offers: ICaseSummaryOffer[] = [];
    isOffered = false;
    hasBeenAccepted = false;

    constructor(
        protected activatedRoute: ActivatedRoute,
        private moduleService: ModuleService,
        private karappatanService: KarappatanService,
        private accountService: AccountService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caseSummary }) => {
            this.caseSummary = caseSummary;
            if (this.caseSummary.moduleId !== undefined) {
                this.moduleService.find(this.caseSummary.moduleId).subscribe(response => {
                    this.module = response.body;
                    this.prepareQuestions();
                });
            }
        });
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            console.log(this.currentAccount);
            this.listOffers();
        });
    }

    previousState() {
        window.history.back();
    }

    private listOffers() {
        this.karappatanService.listOffers(this.caseSummary.id).subscribe(response => {
            this.offers = response.body;
            this.isOffered = false;
            this.hasBeenAccepted = false;
            this.offers.forEach(offer => {
                if (offer.lawyer.login === this.currentAccount.login) {
                    this.isOffered = true;
                }

                if (offer.status === OfferStatus.ACCEPTED) {
                    this.hasBeenAccepted = true;
                }
            });
        });
    }

    accept() {
        this.karappatanService.accept(this.caseSummary.id).subscribe(response => {
            this.caseSummary = response.body;
        });
    }

    prepareQuestions() {
        const firstQuestion = this.module.questions.find(question => question.id === this.module.firstQuestionId);
        this.crawlQuestionTree(firstQuestion);

        // Incase not all question was not properly crawled
        this.caseSummary.answerIds.map(answerId => {
            // Check if there are still userAnswer that is not yet displayed
            const addedQuestion = this.sortedQuestions.find(
                sortedQuestion => sortedQuestion.answers.find(answer => answer.id === answerId) !== undefined
            );
            if (addedQuestion === undefined) {
                const notDisplayedQuestion = this.module.questions.find(
                    question => question.answers.find(answer => answer.id === answerId) !== undefined
                );
                this.crawlQuestionTree(notDisplayedQuestion);
            }
        });
    }

    crawlQuestionTree(firstQuestion: IQuestion) {
        if (firstQuestion !== undefined) {
            this.sortedQuestions.push(firstQuestion);

            const userAnswer: IAnswer = this.getUserAnswer(firstQuestion);
            // Get User's Answer
            if (userAnswer !== undefined) {
                if (userAnswer.recommendationContent != null) {
                    this.recommendations.push(userAnswer.recommendationContent);
                }
                // Get Next Question and add to sortedQuestions
                const nextQuestion = this.module.questions.find(question => question.id === userAnswer.nextQuestionId);
                if (nextQuestion !== undefined) {
                    this.crawlQuestionTree(nextQuestion);
                }
            }
        }
    }

    private getUserAnswer(question: IQuestion): IAnswer {
        return question.answers.find(answer => {
            return this.caseSummary.answerIds.indexOf(answer.id) > -1;
        });
    }

    private offer() {
        this.karappatanService.offer(this.caseSummary.id).subscribe(response => {
            this.listOffers();
        });
    }
}
