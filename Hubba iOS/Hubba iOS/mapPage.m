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
@synthesize SearchBar, AddLocation, ShowAll, tabBar, mapView, TableView;

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
// ----------------------------------------------------------------



// ----------------------------------------------------------------
// table view delegate methods
- (void)tabBar:(UITabBar *)tabBar didSelectItem:(UITabBarItem *)item
{
  NSLog(@"tab selected: %@, %li", item.title, (long)item.tag);
  if(item.tag == 1) [TableView setHidden:YES], [mapView setHidden:NO];
  else [TableView setHidden:NO], [mapView setHidden:YES];
  
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
  [ShowAll setSelected:YES];
  [tabBar setSelectedItem:[tabBar.items objectAtIndex:0]];
  [AddLocation.titleLabel setTextAlignment:NSTextAlignmentRight];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(IBAction)showAllButton:(id)sender{
  [ShowAll setSelected: !ShowAll.selected];
}
@end
