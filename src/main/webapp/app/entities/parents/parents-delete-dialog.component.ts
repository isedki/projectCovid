import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParents } from 'app/shared/model/parents.model';
import { ParentsService } from './parents.service';

@Component({
  templateUrl: './parents-delete-dialog.component.html',
})
export class ParentsDeleteDialogComponent {
  parents?: IParents;

  constructor(protected parentsService: ParentsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parentsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('parentsListModification');
      this.activeModal.close();
    });
  }
}
