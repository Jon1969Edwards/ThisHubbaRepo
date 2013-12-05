//
//  mapPage.h
//  Hubba iOS
//
//  Created by Jackson on 10/30/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "placeView.h"
#import "TableCell.h"

@interface mapPage : UIViewController

  @property (nonatomic, strong) NSArray *locationList;
  @property (nonatomic, strong) NSMutableArray *locationListTrimmed;


  @property (nonatomic, strong) IBOutlet UITabBar *tabBar;
  
  // MY SPOTS and SPOT LIST items
  @property (nonatomic, strong) IBOutlet UIButton *ShowAll;
  @property (nonatomic, strong) IBOutlet UISearchBar *SearchBar;
  @property (nonatomic, strong) IBOutlet UIButton *AddLocation;


  // user defaults
  @property (nonatomic, strong) NSUserDefaults *defaults;

  // MAP item
  @property (nonatomic, strong) IBOutlet MKMapView *mapView;

  // table items
  @property (nonatomic, strong) IBOutlet UITableView *TableView;

  // settings items.
  @property (nonatomic, strong) IBOutlet UIView *settingsInfoView;
  @property (nonatomic, strong) IBOutlet UIButton *Logout;
  @property (nonatomic, strong) IBOutlet UIButton *updateInfo;
  @property (nonatomic, strong) IBOutlet UISegmentedControl *sortBy;

-(IBAction)showAllButton:(id)sender;
-(IBAction)logout:(id)sender;
-(IBAction)setSortItem:(id)sender;

@end
