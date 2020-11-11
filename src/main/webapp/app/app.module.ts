import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Version1SharedModule } from 'app/shared/shared.module';
import { Version1CoreModule } from 'app/core/core.module';
import { Version1AppRoutingModule } from './app-routing.module';
import { Version1HomeModule } from './home/home.module';
import { Version1EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    BrowserModule,
    Version1SharedModule,
    Version1CoreModule,
    Version1HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Version1EntityModule,
    Version1AppRoutingModule,
    BrowserAnimationsModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class Version1AppModule {}
