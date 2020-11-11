import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParents } from 'app/shared/model/parents.model';

@Component({
  selector: 'jhi-parents-detail',
  templateUrl: './parents-detail.component.html',
})
export class ParentsDetailComponent implements OnInit {
  parents: IParents | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parents }) => (this.parents = parents));
  }

  previousState(): void {
    window.history.back();
  }
}
