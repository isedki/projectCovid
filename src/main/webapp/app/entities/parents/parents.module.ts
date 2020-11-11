import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Version1SharedModule } from 'app/shared/shared.module';
import { ParentsComponent } from './parents.component';
import { ParentsDetailComponent } from './parents-detail.component';
import { ParentsUpdateComponent } from './parents-update.component';
import { ParentsDeleteDialogComponent } from './parents-delete-dialog.component';
import { parentsRoute } from './parents.route';

@NgModule({
  imports: [Version1SharedModule, RouterModule.forChild(parentsRoute)],
  declarations: [ParentsComponent, ParentsDetailComponent, ParentsUpdateComponent, ParentsDeleteDialogComponent],
  entryComponents: [ParentsDeleteDialogComponent],
})
export class Version1ParentsModule {}
