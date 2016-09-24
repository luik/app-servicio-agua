import {Component, OnInit} from '@angular/core';
import {IConfig} from "../model/IConfig";
import {ConfigService} from "../services/config.service";

@Component({
    selector: 'app-config-list',
    templateUrl: 'config-list.component.html',
    styleUrls: ['config-list.component.css'],
})
export class ConfigListComponent implements OnInit {

    isDisplayingDialog: boolean = false;
    isNewConfig: boolean;
    configs: IConfig[];
    selectedConfig: IConfig;
    config: IConfig;

    constructor(private configService: ConfigService) {
    }

    ngOnInit() {
        this.configService.getConfigs().subscribe(
            configs =>
            {
                this.configs = configs as Array<IConfig>;
            }
        )
    }

    showDialogToAdd(){
        this.isNewConfig = true;
        this.config = <IConfig>{id: 0};
        this.isDisplayingDialog = true;
    }

    save(){
        if(this.isNewConfig)
        {
        }
        else
        {
            this.configService.updateConfig(this.config).subscribe(
                response =>
                {
                    this.ngOnInit();
                    this.isDisplayingDialog = false;
                }
            );
        }
    }

    onRowSelect(event) {
        this.config = this.cloneConfig(event.data);
        this.isNewConfig = false;
        this.isDisplayingDialog = true;
    }

    cloneConfig(config: IConfig): IConfig {
        let newConfig = <IConfig>{};
        for(let prop in config) {
            newConfig[prop] = config[prop];
        }
        return newConfig;
    }

}
