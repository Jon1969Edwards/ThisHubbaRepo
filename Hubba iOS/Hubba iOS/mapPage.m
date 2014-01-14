//
//  mapPage.m
//  Hubba iOS
//
//  Created by Jackson on 10/30/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "mapPage.h"

@interface mapPage () <UITabBarDelegate, UITableViewDataSource, UITableViewDelegate, UISearchBarDelegate, UISearchDisplayDelegate, MKMapViewDelegate>

@end

@implementation mapPage
@synthesize SearchBar, AddLocation, ShowAll, tabBar, mapView, TableView, settingsInfoView;


-(void)sendDataBack:(NSArray *)array{
  // data sent back from placeView to view on map;
  
  NSString *latitude = [array objectAtIndex:0];
  NSString *longitude = [array objectAtIndex:1];
  
  CLLocationDegrees lat = latitude.floatValue;
  CLLocationDegrees lng = longitude.floatValue;
  NSLog(@"latitude: %f longitude: %f", lat, lng);
  CLLocation *centerOn = [[CLLocation alloc] initWithLatitude:lat longitude:lng];
  [mapView setCenterCoordinate:[centerOn coordinate] animated:YES];
  MKPointAnnotation *annotation = [[MKPointAnnotation alloc] init];
  [annotation setCoordinate:[centerOn coordinate]];
  [annotation setTitle:@"Title"]; //You can set the subtitle too
  [mapView addAnnotation:annotation];
  [self selectItemTab:[self.tabBar.items objectAtIndex:0]];
  [self.tabBar setSelectedItem:[self.tabBar.items objectAtIndex:0]];
}




// ----------------------------------------------------------------
// map view delegate methods
- (void)mapView:(MKMapView *)mapView didSelectAnnotationView:(MKAnnotationView *)view{
  UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc]
                                 initWithTarget:self action:@selector(handlePinButtonTap:)];
  tap.numberOfTapsRequired = 1;
  [view addGestureRecognizer:tap];
}
-(void)handlePinButtonTap:(UITapGestureRecognizer*)gesture{
  placeView *newVC = [[placeView alloc] init];
  newVC.delegate = self;
  [self.navigationController pushViewController:newVC animated:YES];
}
// ----------------------------------------------------------------



// ----------------------------------------------------------------
// table view required delegate methods
-(void) reloadTable{
  id userLocation = [mapView userLocation];
  NSMutableArray *pins = [[NSMutableArray alloc] initWithArray:[mapView annotations]];
  if ( userLocation != nil ) [pins removeObject:userLocation]; // avoid removing user location off the map
  [mapView removeAnnotations:pins];
  [TableView reloadData];
}
-(NSInteger)tableView: tableView numberOfRowsInSection:(NSInteger)section{
  return [self.locationListTrimmed count];
}
-(UITableViewCell *)tableView:tv cellForRowAtIndexPath:(NSIndexPath *)indexPath{
  TableCell *cell = [tv dequeueReusableCellWithIdentifier:@"CELL" forIndexPath:indexPath];

  [cell.name setText: [self.locationListTrimmed objectAtIndex:indexPath.row]];
  
  [cell.name setTag:1];
  [cell.dist setTag:2];
  [cell.diff setTag:3];
  [cell.bust setTag:4];

  return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
  [tableView deselectRowAtIndexPath:indexPath animated:YES];
  [self.view endEditing:YES];
  UITableViewCell *cell = [TableView cellForRowAtIndexPath:indexPath];
  placeView *newVC = [[placeView alloc] init];
  newVC.delegate = self;
  UILabel *help = (UILabel *)[cell viewWithTag:1];
  [newVC setHelpName: help ];
  help = (UILabel *)[cell viewWithTag:2];
  [newVC setHelpDist: help];
  help = (UILabel *)[cell viewWithTag:3];
  [newVC setHelpDiff: help];
  help = (UILabel *)[cell viewWithTag:4];
  [newVC setHelpBust: help];
  NSLog(@"%@", help.text);
  [self.navigationController pushViewController:newVC animated:YES];
  
}
// ----------------------------------------------------------------



// ----------------------------------------------------------------
// tabbar delegate methods
- (void)tabBar:(UITabBar *)tabBar didSelectItem:(UITabBarItem *)item
{
  [self.view endEditing:YES];
  [self selectItemTab:item];
}
-(void)selectItemTab:(UITabBarItem *)item{
  NSLog(@"tab selected: %@, %li", item.title, (long)item.tag);
  if(item.tag == 1) [TableView setHidden:YES], [mapView setHidden:NO], [settingsInfoView setHidden:YES];
  else if(item.tag == 4) [TableView setHidden:YES], [mapView setHidden:YES], [settingsInfoView setHidden:NO];
  else [TableView setHidden:NO], [mapView setHidden:YES], [settingsInfoView setHidden:YES];
  [self reloadTable];
}
// ----------------------------------------------------------------



// ----------------------------------------------------------------
// searchbar delegate methods
#pragma mark UISearchBarDelegate
- (void)searchBarTextDidBeginEditing:(UISearchBar *)searchBar {
  // only show the status bar's cancel button while in edit mode
  [self.SearchBar setAutocorrectionType:UITextAutocorrectionTypeNo];
  // flush the previous search content
}
-(void)searchBarSearchButtonClicked:(UISearchBar *)searchBar{
  [searchBar resignFirstResponder];
}
- (void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText
{
  [self.locationListTrimmed removeAllObjects];// remove all data that belongs to previous search
  if([searchText isEqualToString:@""]||searchText==nil){
    [self.locationListTrimmed addObjectsFromArray:self.locationList];
    [self sortSource];
    [self reloadTable];

    return;
  }
  else{
    NSString *lower = [searchText lowercaseString];
    for(NSString *obj in self.locationList)
    {
      NSRange r = [[obj lowercaseString] rangeOfString:lower];
      if(r.location != NSNotFound){
        [self.locationListTrimmed addObject:obj];
      }
    }
    [self sortSource];
    [self reloadTable];
  }
}
-(void) sortSource{
  if( [self.sortBy selectedSegmentIndex] == 0 ){
    NSLog(@"Sort by name");
    NSMutableArray *tmp = [[NSMutableArray alloc] init];
    [tmp addObjectsFromArray:[self.locationListTrimmed sortedArrayUsingSelector:@selector(localizedCaseInsensitiveCompare:)]];
    [self.locationListTrimmed removeAllObjects];
    [self.locationListTrimmed addObjectsFromArray:tmp];
    
    [tmp removeAllObjects];
    [tmp addObjectsFromArray:[self.locationList sortedArrayUsingSelector:@selector(localizedCaseInsensitiveCompare:)]];
    [self.locationList removeAllObjects];
    [self.locationList addObjectsFromArray:tmp];
    
    [self.TableView reloadData];
  }
  else{

  }
}
// ----------------------------------------------------------------


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
  
  [[UIApplication sharedApplication] setStatusBarStyle:UIStatusBarStyleLightContent];
  
  self.defaults = [NSUserDefaults standardUserDefaults];
  if([self.defaults objectForKey:@"sortBy"] == NULL) [self.sortBy setSelectedSegmentIndex:0];
  else{
    NSString *num = [self.defaults objectForKey:@"sortBy"];
    [self.sortBy setSelectedSegmentIndex:num.intValue-1];
  }
  
  [tabBar setSelectedItem:[tabBar.items objectAtIndex:0]];
  [AddLocation.titleLabel setTextAlignment:NSTextAlignmentRight];
  [mapView setMapType:MKMapTypeSatellite];
  [mapView setDelegate:self];
  
  [self.Logout.layer setBorderWidth:1];
  [self.Logout.layer setBorderColor:[UIColor whiteColor].CGColor];
  [self.Logout.layer setCornerRadius:6];
  [self.updateInfo.layer setBorderWidth:1];
  [self.updateInfo.layer setBorderColor:[UIColor whiteColor].CGColor];
  [self.updateInfo.layer setCornerRadius:6];
  
  [TableView registerClass:[TableCell class] forCellReuseIdentifier:@"CELL"];
  
  self.locationList = [[NSMutableArray alloc] initWithObjects:@"Jack Park", @"Jack SkatePark", @"MotorCity", @"CoolPark", nil];
  self.locationListTrimmed = [[NSMutableArray alloc] init];
  
  for(NSString *s in self.locationList) [self.locationListTrimmed addObject:s];
  [self sortSource];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)showAllButton:(id)sender{
  [self.SearchBar setText:@""];
  [self.view endEditing:YES];
  [SearchBar resignFirstResponder];
  [self.locationListTrimmed removeAllObjects];
  [self.locationListTrimmed addObjectsFromArray:self.locationList];
  [self reloadTable];
}
-(IBAction)logout:(id)sender{
  [self.navigationController popToRootViewControllerAnimated:YES];
}
-(IBAction)setSortItem:(id)sender{
  NSLog(@"sort by: %i", self.sortBy.selectedSegmentIndex);
  [self sortSource];
  [self.defaults setObject:[NSString stringWithFormat:@"%i", self.sortBy.selectedSegmentIndex+1] forKey:@"sortBy"];
  [self.defaults synchronize];
}

@end
