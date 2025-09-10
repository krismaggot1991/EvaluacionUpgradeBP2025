import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { App} from './app';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],      // ⬅️ si AppComponent es standalone
      providers: [provideRouter([])] // ⬅️ provee ActivatedRoute/Router
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    // Mantén tu aserción original aquí (h1, .title, etc.)
    // p.ej: expect(fixture.nativeElement.querySelector('h1')?.textContent).toContain('...');
  });
});
