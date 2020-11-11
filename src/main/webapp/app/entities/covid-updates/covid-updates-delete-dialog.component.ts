import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICovidUpdates } from 'app/shared/model/covid-updates.model';
import { CovidUpdatesService } from './covid-updates.service';

@Component({
  templateUrl: './covid-updates-delete-dialog.component.html',
})
export class CovidUpdatesDeleteDialogComponent {
  covidUpdates?: ICovidUpdates;

  constructor(
    protected covidUpdatesService: CovidUpdatesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.covidUpdatesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('covidUpdatesListModification');
      this.activeModal.close();
    });
  }
}
