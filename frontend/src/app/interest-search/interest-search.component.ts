import { Component, Output, EventEmitter } from '@angular/core';
import { InterestDataService } from '../services/interest-service/interest-data.service';

export class Interest {
  id: string | null;
  name: string;

  constructor(id:string | null = null, name: string) {
    this.id = id;
    this.name = name;
  }
}


@Component({
  selector: 'app-interest-search',
  templateUrl: './interest-search.component.html',
  styleUrls: ['./interest-search.component.sass']
})
export class InterestSearchComponent {

  allInterests: Interest[] = [];
  filteredInterest: Interest[] = [];
  selectedInterest: Interest[] = [];
  searchTerm: string = ''; 
  @Output() selectedInterestChange: EventEmitter<Interest[]> = new EventEmitter();

  constructor(
    private interestDataService: InterestDataService
  ) {}

  ngOnInit(): void {
    this.fetchAllInterests();
  }

    fetchAllInterests() {
      this.interestDataService.listAllInterests().subscribe(
        (response: Interest[]) => {
          this.allInterests = response;
        },
        (error: any) => {
          console.error('Error fetching skills:', error);
        }
      );
    }

  onSearch(searchTerm: string) {
    this.searchTerm = searchTerm.trim();

    if (!searchTerm || searchTerm === '') {
      this.filteredInterest = [];
    } else {
      this.filteredInterest = this.allInterests.filter(interest =>
        interest.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
  }
  

  onAddInterest(interest: Interest) {
    if (!this.selectedInterest.includes(interest)) {
      this.selectedInterest.push(interest);
      this.selectedInterestChange.emit(this.selectedInterest);
      if (!this.allInterests.includes(interest)) {
        this.interestDataService.saveInterest({ name: interest.name }).subscribe(
          (response: any) => {
            console.log('Interest added to the database:', response);
          },
          (error: any) => {
            console.error('Error adding interest to the database:', error);
          }
        );
      }
    }
  }

  onEnterKeyPress(event: Event) {
    const searchTerm = this.searchTerm.trim();
    console.log("searchTerm: " + searchTerm);
    if (event instanceof KeyboardEvent && event.key === 'Enter' && searchTerm && this.filteredInterest.length === 0) {
      const newInterest = new Interest(null, searchTerm);
      this.onAddInterest(newInterest);
    }
  }

  getInterestName(interest: Interest) {
    return interest.name ? interest.name : '';
  }
  
}
