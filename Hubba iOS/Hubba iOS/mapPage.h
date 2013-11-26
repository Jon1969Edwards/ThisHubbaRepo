//
//  mapPage.h
//  Hubba iOS
//
//  Created by Jackson on 10/30/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>

@interface mapPage : UIViewController
  
  @property (nonatomic, strong) IBOutlet UITabBar *tabBar;
  
  // MY SPOTS and SPOT LIST items
  @property (nonatomic, strong) IBOutlet UIButton *ShowAll;
  @property (nonatomic, strong) IBOutlet UISearchBar *SearchBar;
  @property (nonatomic, strong) IBOutlet UITableView *TableView;
  @property (nonatomic, strong) IBOutlet UIButton *AddLocation;
  
  // MAP item
  @property (nonatomic, strong) IBOutlet MKMapView *mapView;


-(IBAction)showAllButton:(id)sender;


@end
