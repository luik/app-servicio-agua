import {Component, OnInit} from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'app-welcome',
    templateUrl: 'welcome.component.html',
    styleUrls: ['welcome.component.css']
})
export class WelcomeComponent implements OnInit {

    public pageTitle: string = 'CONTROL SERVICIO DE AGUA';

    constructor() {
    }

    ngOnInit() {
    }

}
