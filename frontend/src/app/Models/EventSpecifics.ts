import { CityList } from "../city/city.component";
import { Event } from "./Event";
import { UserEvents } from "./UserEvents";

export interface EventSpecifics{
    date: Date;
    events: Event[];
    cities: CityList[];
    userEvents: UserEvents[];
}