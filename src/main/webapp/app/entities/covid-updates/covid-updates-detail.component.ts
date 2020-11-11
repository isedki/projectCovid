import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICovidUpdates } from 'app/shared/model/covid-updates.model';

@Component({
  selector: 'jhi-covid-updates-detail',
  templateUrl: './covid-updates-detail.component.html',
})
export class CovidUpdatesDetailComponent implements OnInit {
  covidUpdates: ICovidUpdates | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ covidUpdates }) => (this.covidUpdates = covidUpdates));
  }

  previousState(): void {
    window.history.back();
  }
}
