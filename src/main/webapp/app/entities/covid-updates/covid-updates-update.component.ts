import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICovidUpdates, CovidUpdates } from 'app/shared/model/covid-updates.model';
import { CovidUpdatesService } from './covid-updates.service';

@Component({
  selector: 'jhi-covid-updates-update',
  templateUrl: './covid-updates-update.component.html',
})
export class CovidUpdatesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [],
    content: [],
    source: [],
    domain: [],
    image: [],
    publishedAt: [],
  });

  constructor(protected covidUpdatesService: CovidUpdatesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ covidUpdates }) => {
      this.updateForm(covidUpdates);
    });
  }

  updateForm(covidUpdates: ICovidUpdates): void {
    this.editForm.patchValue({
      id: covidUpdates.id,
      title: covidUpdates.title,
      content: covidUpdates.content,
      source: covidUpdates.source,
      domain: covidUpdates.domain,
      image: covidUpdates.image,
      publishedAt: covidUpdates.publishedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const covidUpdates = this.createFromForm();
    if (covidUpdates.id !== undefined) {
      this.subscribeToSaveResponse(this.covidUpdatesService.update(covidUpdates));
    } else {
      this.subscribeToSaveResponse(this.covidUpdatesService.create(covidUpdates));
    }
  }

  private createFromForm(): ICovidUpdates {
    return {
      ...new CovidUpdates(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      content: this.editForm.get(['content'])!.value,
      source: this.editForm.get(['source'])!.value,
      domain: this.editForm.get(['domain'])!.value,
      image: this.editForm.get(['image'])!.value,
      publishedAt: this.editForm.get(['publishedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICovidUpdates>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
