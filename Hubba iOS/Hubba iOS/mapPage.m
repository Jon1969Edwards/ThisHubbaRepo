//
//  mapPage.m
//  Hubba iOS
//
//  Created by Jackson on 10/30/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "mapPage.h"

@interface mapPage () <UITabBarDelegate, UITableViewDataSource, UITableViewDelegate, UISearchBarDelegate>

@end

@implementation mapPage
@synthesize SearchBar, AddLocation, ShowAll, tabBar, mapView, TableView, settingsInfoView;

// ----------------------------------------------------------------
// table view required delegate methods
-(NSInteger)tableView: tableView numberOfRowsInSection:(NSInteger)section{
  return 1;
}
-(UITableViewCell *)tableView:tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
  
  UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"myCell"];
  if (cell == nil) {
    cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:@"myCell"];
  }
  return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
  
  [tableView deselectRowAtIndexPath:indexPath animated:YES];
  placeView *newVC = [[placeView alloc] init];
  [self.navigationController pushViewController:newVC animated:YES];
  
}
// ----------------------------------------------------------------



// ----------------------------------------------------------------
// table view delegate methods
- (void)tabBar:(UITabBar *)tabBar didSelectItem:(UITabBarItem *)item
{
  NSLog(@"tab selected: %@, %li", item.title, (long)item.tag);
  if(item.tag == 1) [TableView setHidden:YES], [mapView setHidden:NO], [settingsInfoView setHidden:YES];
  else if(item.tag == 4) [TableView setHidden:YES], [mapView setHidden:YES], [settingsInfoView setHidden:NO];
  else [TableView setHidden:NO], [mapView setHidden:YES], [settingsInfoView setHidden:YES];
  [TableView reloadData];
}
// ----------------------------------------------------------------



// ----------------------------------------------------------------
// searchbar delegate methods

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
  [tabBar setSelectedItem:[tabBar.items objectAtIndex:0]];
  [AddLocation.titleLabel setTextAlignment:NSTextAlignmentRight];
  [mapView setMapType:MKMapTypeSatellite];
  
  [self.Logout.layer setBorderWidth:1];
  [self.Logout.layer setBorderColor:[UIColor whiteColor].CGColor];
  [self.Logout.layer setCornerRadius:6];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)searchBarSearchButtonClicked:(UISearchBar *)searchBar{
  [searchBar resignFirstResponder];
  [TableView reloadData];
  [mapView reloadInputViews];
}

-(IBAction)showAllButton:(id)sender{
  [self.SearchBar setText:@""];
  [self.view endEditing:YES];
  [SearchBar resignFirstResponder];
  [TableView reloadData];
  [mapView reloadInputViews];
}
-(IBAction)logout:(id)sender{
  [self.navigationController popToRootViewControllerAnimated:YES];
}
@end
