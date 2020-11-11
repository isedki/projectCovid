import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Version1SharedModule } from 'app/shared/shared.module';
import { CovidUpdatesComponent } from './covid-updates.component';
import { CovidUpdatesDetailComponent } from './covid-updates-detail.component';
import { CovidUpdatesUpdateComponent } from './covid-updates-update.component';
import { CovidUpdatesDeleteDialogComponent } from './covid-updates-delete-dialog.component';
import { covidUpdatesRoute } from './covid-updates.route';

@NgModule({
  imports: [Version1SharedModule, RouterModule.forChild(covidUpdatesRoute)],
  declarations: [CovidUpdatesComponent, CovidUpdatesDetailComponent, CovidUpdatesUpdateComponent, CovidUpdatesDeleteDialogComponent],
  entryComponents: [CovidUpdatesDeleteDialogComponent],
})
export class Version1CovidUpdatesModule {}
