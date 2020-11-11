import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParents, Parents } from 'app/shared/model/parents.model';
import { ParentsService } from './parents.service';

@Component({
  selector: 'jhi-parents-update',
  templateUrl: './parents-update.component.html',
})
export class ParentsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected parentsService: ParentsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parents }) => {
      this.updateForm(parents);
    });
  }

  updateForm(parents: IParents): void {
    this.editForm.patchValue({
      id: parents.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parents = this.createFromForm();
    if (parents.id !== undefined) {
      this.subscribeToSaveResponse(this.parentsService.update(parents));
    } else {
      this.subscribeToSaveResponse(this.parentsService.create(parents));
    }
  }

  private createFromForm(): IParents {
    return {
      ...new Parents(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParents>>): void {
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
