import { Component } from '@angular/core';
import { CityList } from '../city/city.component';

export class CountryList{
  constructor(
    public id: string,
    public name: string,
    public code: string,
    public cities: CityList[]
  ){}

  public getName(): string {
    return this.name;
  }
}



@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.sass']
})
export class CountryComponent {

}
