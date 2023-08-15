import { CityComponent } from "../city/city.component";
import { Event } from "./Event";
import { UserEvents } from "./UserEvents";

export interface EventSpecifics{
    date: Date;
    events: Event[];
    cities: CityComponent[];
    userEvents: UserEvents[];
}