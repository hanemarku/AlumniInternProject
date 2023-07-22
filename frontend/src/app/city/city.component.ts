import { Component } from '@angular/core';

export class CityList{
  constructor(
    public id: string,
    public name: string,
    public countryId: string
  ){}
}

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.sass']
})
export class CityComponent {

}
