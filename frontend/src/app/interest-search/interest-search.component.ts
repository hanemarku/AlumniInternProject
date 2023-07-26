import { Component, Output, EventEmitter } from '@angular/core';

export class Interest {
  constructor(public name: string) {}
}


@Component({
  selector: 'app-interest-search',
  templateUrl: './interest-search.component.html',
  styleUrls: ['./interest-search.component.sass']
})
export class InterestSearchComponent {
  allInterests: string[] = [
    'Digital Marketing',
    'Web Development',
    'Graphic Design',
    'Data Analysis',
    'Project Management',
    'Communication Skills',
    'Problem Solving',
    'Teamwork',
    'Time Management',
    'Leadership'
  ];

  filteredInterest: string[] = [];
  selectedInterest: string[] = [];
  searchTerm: string = ''; 
  @Output() selectedInterestChange: EventEmitter<string[]> = new EventEmitter();

  onSearch(searchTerm: string) {
    this.searchTerm = searchTerm.trim();

    if (!searchTerm || searchTerm === '') {
      this.filteredInterest = [];
    } else {
      this.filteredInterest = this.allInterests.filter(interest =>
        interest.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
  }
  

  onAddInterest(interest: string) {
    if (!this.selectedInterest.includes(interest)) {
      this.selectedInterest.push(interest);
      this.selectedInterestChange.emit(this.selectedInterest);
    }
  }

  onEnterKeyPress(event: Event) {
    const searchTerm = this.searchTerm.trim();
    console.log("searchTerm: " + searchTerm);
    if (event instanceof KeyboardEvent && event.key === 'Enter' && searchTerm && !this.filteredInterest.length) {
      this.onAddInterest(searchTerm);
    }
  }
}
